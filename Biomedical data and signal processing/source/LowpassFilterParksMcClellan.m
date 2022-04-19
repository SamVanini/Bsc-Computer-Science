function [B, err] = LowpassFilterParksMcClellan ()

% Constants of the filter
fp = 24; % Passband frequency [0,fp]  
fs = 26; % Stopband frequency [fs,Fs/2]      
Fs = 128; % Sampling rate
Wp = fp/(Fs/2); % Sup frequency passband [Normalized]
Ws = fs/(Fs/2); % Inf frequency passband [Normalized]

f = [0 Wp Ws 1];
m = [1 1 0 0];
n = 100; % Order of the filter

% Construction of the FIR filter
[B, err] = firpm(n,f,m);