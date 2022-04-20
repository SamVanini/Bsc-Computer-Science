clc; clear all;

url = 'https://images.immediate.co.uk/production/volatile/sites/4/2009/07/GettyImages-931270318-43ab672.jpg?quality=90&resize=940%2C400';
ss = imread(url);
image(ss);
picture = imresize(ss,[227,227]);
label = classify(alexnet, picture);
image(picture); 
title(char(label));
