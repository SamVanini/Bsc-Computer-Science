tic
vec1 = 1:200000;
vec2 = vec1;
for i = 1:length(vec1)
    vec1(i) = vec1(i) + 3; 
end
toc

tic
vec2 = vec2 + 3;
toc
