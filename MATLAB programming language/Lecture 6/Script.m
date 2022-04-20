%codice inerente alla lezione Matlab del 17/12/2021
%Docente Maris Bogdan

%%
%codice per la creazione di un immagine random (rumore) e rappresentazione 
%dell'immagine utilizzando colormap

immagine = randi([1 6],200);
image(immagine)
colormap(gray);
image(immagine)

%esercizio: cambiare colormap utilizzando le mappe predefinite di matlab
%oppure una mappa personalizzata

%Svolto durante il laboratorio di ESI

%%
%%codice per il caricamento dell'immagine 'Lena' :-), 
%visualizzazione dell'istogamma e equalizzazione per incrementare
%il contrasto
I = imread('https://www.researchgate.net/profile/Sukadev-Meher/publication/37394153/figure/fig1/AS:669375770140688@1536603028447/a-The-original-Lena-image-b-The-Lena-face-Test-Image_W640.jpg');
imshow(I)
imhist(I)
J = histeq(I);
help histeq
figure
subplot(1,2,1)
imshow(I)
subplot(1,2,2)
imshow(J)
%esercizio: provate lo stesso metodo per un immagine con un contrasto
%ridotto

%Svolto durante il laboratorio di ESI (lab 4 parte 1)

%%
%%codice per il thresholding con la segmentazione binaria di un immagine
%in background e foreground

I = imread('coins.png');
level = graythresh(I);
BW = imbinarize(I,level);
figure; subplot(1,2,1); imshow(I); subplot(1,2,2); imshow(BW)

%esercizio (avanzato): identificare e visualizzare tutte le circonferenze dell'immagine BW
%Hint: utilizzare le funzioni imfindcircles viscircles (vedi lezione 1)


%%
%%codice per il filtraggio spaziale di un immagine utilizzando
%operazioni di convoluzione
A = imread('trees.tif'); %immagine uint8
blur_filter = 1/9*ones(3); %filtro media 3x3
sobel_filter_h = [1 2 1;0 0 0; -1 -2 -1]; %filtro di convoluzione per gli edge orizzontali
sobel_filter_v = [1 2 1;0 0 0; -1 -2 -1]'; %filtro di convoluzione per gli edge verticali
B = conv2(blur_filter,A); %convoluzione
C = conv2(sobel_filter_v,A); %convoluzione
D = conv2(sobel_filter_h,A); %convoluzione
figure
colormap gray
subplot(2,2,1)
image(A); title('originale');
subplot(2,2,2)
imagesc(B); title('blurred');
subplot(2,2,3)
imagesc(C); title('vertical edges');
subplot(2,2,4)
imagesc(D); title('horizontal edges');

%esercizio: applicare altri filtri utilizzando fspecial (hint: help
%fspecial)

%Svolto durante il laboratorio di ESI (lab4 parte 2)
