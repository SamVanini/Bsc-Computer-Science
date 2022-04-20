%Create a function matrowsum to calculate and return a vector of all of 
%the row sums of a matrix, instead of column sums 
%(sum function in Matlab returns the column sums)
function sum_vec = matrowsum(A)
    r = size(A,1);
    sum_vec = zeros(1,r);
    for i = 1:r
        sum_vec(i) = somma_riga(A(i,:));
    end
    
function scalare = somma_riga(vettore)
    scalare = 0;
    for j=1:length(vettore)
        scalare=scalare+vettore(j);
    end
end
end

        
