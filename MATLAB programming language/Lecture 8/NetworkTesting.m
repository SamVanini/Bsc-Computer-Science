I = imread("MerchDataTest.jpg");
I = imresize(I, [227 227]);%resize the image to fit the size of the network input
[YPred,probs] = classify(trainedNetwork_1,I); %use the weighths computed previously
imshow(I);
label = YPred;
title(string(label) + ", " + num2str(100*max(probs),3) + "%");
