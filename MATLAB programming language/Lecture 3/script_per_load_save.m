load('objweights.mat') %carica la variabile dal file
y = round(objweights); %approssima ad un intero
x = 1:length(y);  % Not necessary
plot(y, 'r*')
xlabel('Object #')
ylabel('Weight')
title('Practice Plot')
