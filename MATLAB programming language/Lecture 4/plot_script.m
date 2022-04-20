x=linspace(0,10);
for i = 1:4
    subplot(2,2,i)
    % create plot i
    y = i.^x;
    plot(x,y)
    titolo = strcat('Esponenziale: ',' ', num2str(i),'^x');
    title(titolo)
end 
