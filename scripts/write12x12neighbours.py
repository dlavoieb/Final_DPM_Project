#!/usr/bin/python
for x in xrange(0,12):
	for y in xrange(0,12):
		print "r"
		print x
		print "c"
		print y
		print ".addNeighbours(new Node[] {r"
		print x
		print "c"
		print y-1
		print ", r"
		print x-1
		print "c"
		print y
		print ", r"
		print x+1
		print "c"
		print y
		print ", r"
		print x
		print "c"
		print y+1
		print "});"
