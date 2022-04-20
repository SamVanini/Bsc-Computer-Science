% This script calculates the volume of a hollow sphere
 
inner = input('Enter the inner radius: ');
outer = input('Enter the outer radius: ');
 
volume = vol_hol_sphere(inner, outer);
 
fprintf('The volume is %.2f\n', volume)
