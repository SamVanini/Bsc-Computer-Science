%funzione che calcola il volume di una sfera vuota
% Calculates the volume of a hollow sphere
function hollvol = vol_hol_sphere(inner, outer)
%controlliamo che i raggi 'esterni' sono maggiori dei raggi 'interni'
if prod(prod(outer-inner>0))
 hollvol = 4/3 * pi * (outer.^3 - inner.^3);
else fprintf('I raggi esterni sono maggiori di quelli interni\n');
end
