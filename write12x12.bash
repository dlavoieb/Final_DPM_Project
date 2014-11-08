for i in 0 1 2 3 4 5 6 7 8 9 10 11 
do
    for j in 0 1 2 3 4 5 6 7 8 9 10 11
    do
        echo "Node r{$i}c{$j} = new Node(new Coordinate($i,$j));" >> file.txt

    done


done
