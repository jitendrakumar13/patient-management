package com.pm.billing_service.grpc;
import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc.BillingServiceImplBase;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@Slf4j
@GrpcService
public class BillingGrpcService extends BillingServiceImplBase {

    @Override
    public void createBillingAccount(BillingRequest billingRequest, StreamObserver<BillingResponse>responseObserver){

        log.info("Received request to create billing account for user: {}", billingRequest.toString());

        //business logic  - eg save to db , perform caculations, etc

        BillingResponse billingResponse = BillingResponse.newBuilder()
                .setAccountId("12345")
                .setStatus("SUCCESS")
                .build();

        responseObserver.onNext(billingResponse);
        responseObserver.onCompleted();

    }






}
