% Acript per la lezione sulla rappresentazione grafica in matlab
% Author Bogdan Maris ...
close all
clf
x = linspace(0,100,1000);
y = sin(x);
z = cos(x);
plot(x,y,'g*--')
hold
plot(x,z,'ro:')
xlabel('Asse delle x')
ylabel('Asse delle y')
title('sin(x), cos(x)')
legend('sin', 'cos');
%axis([0 100 -50 50])
axis equal
grid

figure
t = 0:pi/50:10*pi; %argomento tra 0 e 10*pi con il passo pi/50
plot3(sin(t),cos(t),t,'ro:'); %un'elica 3D
xlabel('Asse delle x')
ylabel('Asse delle y')
zlabel('Asse delle z')
axis equal
grid