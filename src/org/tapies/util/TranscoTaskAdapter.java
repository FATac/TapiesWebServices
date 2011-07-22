package org.tapies.util;

import org.i2cat.tapies.transcoder.entities.SubTask;
import org.i2cat.tapies.transcoder.entities.SubTaskInputParameters;
import org.i2cat.tapies.transcoder.entities.Task;
import org.i2cat.tapies.transcoder.entities.TaskInputParameters;
import org.i2cat.tapies.transcoder.entities.TranscodingTask;
import org.i2cat.tapies.transcoder.transco.extras.tools.ImageTool;
import org.tapies.rest.entities.Profile;
import org.tapies.rest.entities.Transco;

public class TranscoTaskAdapter {

	private static final int SUBTASK_ID_MULTIPLIER = 100;

	public static Task fromTransco (Transco transco) {
		
		int counter = 0;
		
		TranscodingTask tt = new TranscodingTask();
		// Copy the id
		tt.setId(transco.getId());
		// Create the common input params
		TaskInputParameters commonIn = new TaskInputParameters();
		commonIn.setOriginalVideoPath(transco.getSrc_path());
		tt.setCommonInputParameters(commonIn);
		
		for (Profile profile : transco.getProfiles()) {
			// Create the subtask 
			SubTask subTask = new SubTask();
			subTask.setId( (transco.getId() * SUBTASK_ID_MULTIPLIER) + counter);
			subTask.setInparameters(createSubTaskInputParameters(profile));
			tt.putSubTask(subTask);	
			
			// Update the counter
			counter++;
		}
		return tt;
	}
	
	public static void updateTranscoFromTask(Transco transco, Task task) {
		
		int counter = 0;
		
		for (Profile profile : transco.getProfiles()) {
			
			profile.setStatus(task.getSubTask(
					(transco.getId() * SUBTASK_ID_MULTIPLIER) + counter)
					.getStatusIntValue());
			counter++;
		}
	}
	

	private static SubTaskInputParameters createSubTaskInputParameters(
			Profile profile) {

		SubTaskInputParameters subTaskParams = new SubTaskInputParameters();
		subTaskParams.setOutVideoPath(profile.getDst_path());
		
		// TODO Profile settings by hand...
		
		subTaskParams.setContainer("mp4");
		// Audio
		subTaskParams.setAudioBitrate("128000");
		subTaskParams.setAudioChannels("2");
		subTaskParams.setAudioCodec("libfaac");
		subTaskParams.setAudioSampleRate("44100");

		// Video
		subTaskParams.setDeinterlace(true);
		subTaskParams.setFps("25");
		subTaskParams.setVideoBitrate("1000000");
		subTaskParams.setVideoCodec("libx264");
		subTaskParams.setVideoBitrateTolerance("100000");
		subTaskParams.setPresetFile("h264presets.txt");
		
		if (profile.getExtra_ops() != null) {
			ImageTool imgTool = new ImageTool(profile.getExtra_ops().getImg(), 25, 25, ImageTool.RIGHT_INF_CORNER_10);
			subTaskParams.putTool(imgTool);
		}

		return subTaskParams;
	}
}
