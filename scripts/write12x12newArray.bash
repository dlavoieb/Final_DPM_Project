echo "graphPlane = new Node[][] {" >> file.txt
for i in 0 1 2 3 4 5 6 7 8 9 10 11 
do
	echo "{" >> file.txt
    for j in 0 1 2 3 4 5 6 7 8 9 10 11
    do
        echo  r"$i"c"$j", >> file.txt

    done

	echo "}," >> file.txt
	echo >> file.txt
done

echo "}" >> file.txt
