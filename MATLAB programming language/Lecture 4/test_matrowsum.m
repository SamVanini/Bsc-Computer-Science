%script per testare la funzione matrowsum
clear
clc
A = randi([0 10],300,400);
B = A';

%%
tic
matrowsum(A);
toc

%%
tic
sum(B);
toc