package com.example;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;

import com.example.Utils;

public class FaceDetection1 {

	public static String basePath=System.getProperty("user.dir");
	public static String classifierPath1="C:/Users/RajeshKannanSubraman/Downloads/opencv/sources/data/haarcascades/haarcascade_frontalface_alt2.xml";
	public static String inpImgFilename="C:/Users/RajeshKannanSubraman/Downloads/face2.jpg";
	public static String opImgFilename="C:/Users/RajeshKannanSubraman/Pictures/OutputImages";



	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.out.println("Library loaded..");
		
		try {
			int c=1;
			for(File f : getAllImageFilesFromFolder(new File ("C:/Users/RajeshKannanSubraman/Pictures/InputImages"))) {
				Mat frame=Imgcodecs.imread(f.getAbsolutePath(), 1);
				if (!frame.empty())
				{
					// face detection
					detectAndDisplay(frame);
					File outputfile = new File(opImgFilename + "/"+c+".jpg");
				    ImageIO.write(Utils.matToBufferedImage(frame), "jpg", outputfile);
				    System.out.println("Done!!");
				    c=c+1;
				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static List<File> getAllImageFilesFromFolder(File directory) {

		  //Get all the files from the folder

		  File[] allFiles = directory.listFiles();

		  if (allFiles == null || allFiles.length == 0) {

		    throw new RuntimeException("No files present in the directory: " + directory.getAbsolutePath());

		  }

		 

		  //Set the required image extensions here.

		  List<String> supportedImageExtensions = Arrays.asList("jpg", "png", "gif", "webp","jpeg");

		 

		  //Filter out only image files

		  List<File> acceptedImages = new ArrayList<>();

		  for (File file : allFiles) {

		    //Parse the file extension

		    String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);

		    //Check if the extension is listed in the supportedImageExtensions

		    if (supportedImageExtensions.stream().anyMatch(fileExtension::equalsIgnoreCase)) {

		      //Add the image to the filtered list

		      acceptedImages.add(file);

		    }

		  }

		 

		  //Return the filtered images

		  return acceptedImages;

		}
	
	public static void detectAndDisplay(Mat frame) throws IOException
	{
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		int absoluteFaceSize=0;
		CascadeClassifier faceCascade=new CascadeClassifier();
		
		faceCascade.load(classifierPath1);
		// convert the frame in gray scale
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		// equalize the frame histogram to improve the result
		Imgproc.equalizeHist(grayFrame, grayFrame);
		
		// compute minimum face size (1% of the frame height, in our case)
		
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0)
			{
				absoluteFaceSize = Math.round(height * 0.01f);
			}
				
		// detect faces
		faceCascade.detectMultiScale(grayFrame, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(absoluteFaceSize, absoluteFaceSize), new Size(height,height));
				
		// each rectangle in faces is a face: draw them!
		Rect[] facesArray = faces.toArray();
		System.out.println("Number of faces detected = "+facesArray.length);
		for (int i = 0; i < facesArray.length; i++)
			Imgproc.rectangle(frame, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 2);
			
	}

}

