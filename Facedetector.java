package com.example;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
  
public class Facedetector {
   public static void main (String[] args) {
      // Loading the OpenCV core library
      System.loadLibrary( Core.NATIVE_LIBRARY_NAME );

      // Reading the Image from the file and storing it in to a Matrix object
      String file ="C:/Users/RajeshKannanSubraman/Downloads/MicrosoftTeams-image.jpeg";
      Mat src = Imgcodecs.imread(file);

      // Instantiating the CascadeClassifier
      String xmlFile = "C:/Users/RajeshKannanSubraman/Downloads/opencv/sources/data/lbpcascades/lbpcascade_frontalface_improved.xml";
      CascadeClassifier classifier = new CascadeClassifier(xmlFile);

      // Detecting the face in the snap
      MatOfRect faceDetections = new MatOfRect();
      classifier.detectMultiScale(src, faceDetections);
      System.out.println(String.format("Detected %s faces", 
         faceDetections.toArray().length));

      // Drawing boxes
      for (Rect rect : faceDetections.toArray()) {
         Imgproc.rectangle(
            src,                                               // where to draw the box
            new Point(rect.x, rect.y),                            // bottom left
            new Point(rect.x + rect.width, rect.y + rect.height), // top right
            new Scalar(0, 0, 255),
            3                                                     // RGB color
         );
      }

      // Writing the image
      Imgcodecs.imwrite("C:/Users/RajeshKannanSubraman/Pictures/facerecognition/shreyaface-detected.jpeg", src);

      System.out.println("Image Processed");
   }
}