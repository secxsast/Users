
package com.amazonaws.samples3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Sdk3 {

        @Value("${app.awsServices.buckName1}")
        private String bucket1;

        @Value("${app.awsServices.buckName2}")
    private String bucket2;
	
	public void Foo() {

		AmazonS3.putObject(bucket1);
		AmazonS3.putObject(bucket2);

	}
}