% la prima riga rappresenta l'help
% Calculates a person's target heart rate
% author Bogdan Maris all right reserved, November 2021
 
age = input('Please enter your age in years: '); %richiede l'età 
thr = (220-age) * 0.6; %calcola target heart rate
fprintf('For a person %d years old,\n', age) %stampa un messaggio
fprintf('the target heart rate is %.1f.\n', thr) %stampa THR
