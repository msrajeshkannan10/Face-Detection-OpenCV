package com.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.dnn.Dnn;
import org.opencv.dnn.Net;
import org.opencv.imgcodecs.Imgcodecs;

public class FaceRecognition {
    public static Mat process(Net net, Mat img) {
        Mat inputBlob = Dnn.blobFromImage(img, 1./255, new Size(96,96), new Scalar(0,0,0), true, false);
        net.setInput(inputBlob);
        return net.forward().clone();
    }
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Net net = Dnn.readNetFromTorch("C:/Users/RajeshKannanSubraman/Downloads/nn4.small2.v1.t7");
        Mat feature1 = process(net, Imgcodecs.imread("C:/Users/RajeshKannanSubraman/pictures/messi1.jpg")); // your data here !
        Mat feature2 = process(net, Imgcodecs.imread("C:/Users/RajeshKannanSubraman/pictures/messi2.jpg")); // your data here !
        double dist  = Core.norm(feature1,  feature2);
        System.out.println(dist);
        
        if (dist < 1)
            System.out.println("SAME !");
        else
        	System.out.println("NOT SAME !");
    }
}