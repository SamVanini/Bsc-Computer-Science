%% Biomedical Signal and Data Processing project script
% Bachelor's degree in Computer Science
% University of Verona - A.Y. 2021/2022
% Authors:
% Bozzini Chiara
% Massagrande Marco
% Vanini Samantha

%% Initial operations 
% Workspace clearing and eeglab path setting

clear all; close all;
clc;

% Insert here your eeglab folder path 
addpath 'C:\Users\Lenovo\Documents\MATLAB\Elaborazione segnali e dati biomedici\eeglab2021.1';

%% Load the EEG file and the channel locations

% Load eeglab
[ALLEEG EEG CURRENTSET ALLCOM] = eeglab; 

% Load *.set data using the function pop_loadset. 
EEG = pop_loadset('testeeglaboratorio.set');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 1

% Plot the data using a window of 50 s using the correct the frequency rate and importing the channel location labels
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});

figure; 
title('Raw'); 
pop_spectopo(ALLEEG(1), 1, [0 1996], 'EEG' , 'percent', 100, 'freq', [6 11 22], 'freqrange',[0 30],'electrodes','on','maplimits', [-8 8]);

%% Baseline removal
% Perform a baseline removal manually, you can check the result comparing
% the content of EEG.data with the output of the function pop_rmbase( ALLEEG(1), [], []); 

% Get the dimensions of the matrix containing the data
[nr nc] = size(ALLEEG(1).data);

% Compute the mean of the matrix (operates on the elements contained in second dimensions of ALLEEG(1))
MEAN = (mean(ALLEEG(1).data, 2));

% Creation of the baseline matrix
baseline = ones(nr ,nc).*MEAN;

% Signal without the mean
EEG.data = ALLEEG(1).data - baseline;

% Create a new dataset with the name EEG_BAS
[ALLEEG EEG CURRENTSET] = pop_newset( ALLEEG, EEG, CURRENTSET, 'setname', 'EEG_BAS');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 2

% Plot the data after baseline removal
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});

% You can compare the new EEG with the previous one:
% eegplot(ALLEEG(1).data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});
figure; 
title('Baseline'); 
pop_spectopo(ALLEEG(2), 1, [0 1996], 'EEG' , 'percent', 100, 'freq', [6 11 22], 'freqrange',[0 30],'electrodes','on','maplimits', [-8 8]);


%% Filtering the data [1-25Hz]
% Apply separately a highpass filter, using the function implemented in
% EEGLAB, and a lowpass filter, using a Back-Forward FIR filter

% Apply an highpass filter using the function pop_eegfilt (FIR filter)
EEG = pop_eegfilt( EEG, 1, 0, [], [0], 0, 1, 'fir1', 0); % highpass filter, cutoff: 1 Hz

% Apply a lowpass filter, created with the function Lowpass_filter, then
% use filtfilt in order to create a Back-Forward filter
% In order to understand how the filter works, you can compare the output
% of the following lines with the EEG filtered by the function EEG = pop_eegfilt( EEG, 0, 25, [], [0], 0, 1, 'fir1', 0); 

% Create the filter
Fs = 128;                               % Given in the project paper
[B, err] = LowpassFilterParksMcClellan();

% Compute the frequency response
[H,F] = freqz(B,1,Fs/2,Fs);

% Plot magnitude and phase of the frequency response
figure(1)
subplot(211)
plot(F,abs(H),'b')
xlabel('Hz')
ylabel('Modulo')
title(['risposta in frequenza (Fs=' num2str(Fs) 'Hz)']);
grid on
subplot(212)
plot(F,angle(H),'r')
ylabel('Fase')
xlabel('Hz')
grid on

Filtered = filtfilt(B, 1, double(EEG.data'));
EEG.data = single(Filtered');

% Create a new dataset with the name EEG_BAS_FIL
[ALLEEG EEG CURRENTSET] = pop_newset(ALLEEG, EEG, CURRENTSET, 'setname', 'EEG_BAS_FIL');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 3

% Plot the data after filtering
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});

% You can compare the new EEG with the previous one:
% eegplot(ALLEEG(2).data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});

figure; 
title('Filtered'); 
pop_spectopo(EEG, 1, [0 1996], 'EEG' , 'percent', 100, 'freq', [6 11 22], 'freqrange',[0 30],'electrodes','on','maplimits', [-8 8]);

%% Re-reference the data (use average reference)

% Re-reference the new dataset with an average reference using the function pop_reref
EEG = pop_reref( EEG, []); 

% Create a new dataset with the name EEG_BAS_FIL_AVE
[ALLEEG EEG CURRENTSET] = pop_newset(ALLEEG, EEG, CURRENTSET, 'setname', 'EEG_BAS_FIL_AVE');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 4

% Plot the data after re-referencing
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});

% figure;
% title('Re-referenced'); 
% pop_spectopo(EEG, 1, [0 1996], 'EEG' , 'percent', 100, 'freq', [6 11 22], 'freqrange',[0 30],'electrodes','on','maplimits', [-8 8]);

%% Interpolate bad channels

%Interpolate bad data channel(s) using the function pop_interp and spherical method
EEG = pop_interp(EEG, 4, 'spherical'); % F3

% Create a new dataset with the name EEG_BAS_FIL_AVE_INT
[ALLEEG EEG CURRENTSET] = pop_newset(ALLEEG, EEG, CURRENTSET, 'setname', 'EEG_BAS_FIL_AVE_INT');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 5

% Plot the data after bad channel(s) removal
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});


%% Run Independent Component Analysis (ICA) (using fastica)

% Apply ICA for data denoising: apply the decomposition using the function pop_runica
[EEG,com] = pop_runica(EEG,'icatype','fastica');

% Create a new dataset with the name EEG_BAS_FIL_AVE_INT_ICA
[ALLEEG EEG CURRENTSET] = pop_newset(ALLEEG, EEG, CURRENTSET, 'setname', 'EEG_BAS_FIL_AVE_INT_ICA');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 6

% Plot the independent components (ICs) before removal using the function 
% pop_eegplot (icacomp - type of rejection 0 = independent components)
pop_eegplot( EEG, 0, 0, 0); 
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'});

% Plot the components maps in 2-D (optional)
pop_topoplot(EEG, 0, [1:size(EEG.icawinv,2)] ,'EEG_BAS_FIL_AVE_INT_ICA',[4 5] ,0,'electrodes','on');

%% ICA denoising

% Visually identify components reflecting eyeblinks, movements, heartbeat,
% and other noises and then remove them using the function pop_subcomp
EEG = pop_subcomp( EEG, 3, 0);  % example of bad components: 3

% Visualize the ICs after removal
pop_eegplot(EEG, 0, 0, 0); 

% Update the EEGLAB window to view changes
eeglab redraw % CURRENTSET still = 6

% Plot the EEG after bad ICs removal
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',50,'color',{'b'}); 


%% Extracting all types of epochs after the events

% Extract epochs time locked to the event '2' and '4' from 0 to 2 s after those time-locking events
EEG = pop_epoch( EEG, {'2' '4'}, [0 2], 'newname', 'EOEC_epochs', 'epochinfo', 'yes');

% Create a new dataset with the name EOEC_epochs
[ALLEEG EEG CURRENTSET] = pop_newset(ALLEEG, EEG, CURRENTSET, 'setname', 'EOEC_epochs');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 7

% Plot the epochs after events (5 epochs per window)
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',12,'color',{'b'}); 


%% Extract epochs Type 2 EC (eyes closed)

% Select epochs time locked to the events '2' EC
EEG = pop_selectevent( EEG,'type',2,'deleteevents','on','deleteepochs','on');

% Create a new dataset with the name EC_epochsT2
[ALLEEG EEG CURRENTSET] = pop_newset(ALLEEG, EEG, CURRENTSET, 'setname', 'EC_epochsT2');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 8

% Plot the epochs Type '2'
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',6,'color',{'b'}); 


%% Extract epochs Type 4 EO (eyes open)

% Select epochs time locked to the events '4' EO
% Pay attention, use the dataset that contains all the epochs and then select only those related to Type 4
EEG = pop_selectevent(ALLEEG(7), 'type',4,'deleteevents','on','deleteepochs','on');

% Create a new dataset with the name EO_epochs_T4
[ALLEEG EEG CURRENTSET] = pop_newset(ALLEEG, EEG, CURRENTSET, 'setname', 'EO_epochsT4');

% Update the EEGLAB window to view changes
eeglab redraw % Now CURRENTSET = 9

% Plot the epochs Type '4'
eegplot(EEG.data,'srate',128,'eloc_file',EEG.chanlocs,'events',EEG.event,'winlength',6,'color',{'b'}); 


%% Calculate power spectra separately for the epochs '2' and epochs '4' with the same maplimits for all the topographic maps

% dataflag: if 1, process the input data channels; if 0, process its component activations
figure; 
title('Type 2 EC (eyes closed)'); 
pop_spectopo(ALLEEG(8), 1, [0 1996], 'EEG' , 'percent', 100, 'freq', [6 11 22], 'freqrange',[2 25],'electrodes','on','maplimits', [-8 8]); 

figure; 
title('Type 4 EO (eyes open)'); 
pop_spectopo(ALLEEG(9), 1, [0 1996], 'EEG' , 'percent', 100, 'freq', [6 11 22], 'freqrange',[2 25],'electrodes','on','maplimits', [-8 8]);

