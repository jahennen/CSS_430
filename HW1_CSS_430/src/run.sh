#!/bin/bash
echo ./processes mingetty
./processes mingetty

echo "ps -A | grep mingetty | wc -l"
ps -A | grep mingetty | wc -l
echo
echo ./processes ksand
./processes ksand

echo "ps -A | grep kscand | wc -l"
ps -A | grep kscand | wc -l
echo
echo ./processes sendmail
./processes sendmail

echo "ps -A | grep sendmail | wc -l"
ps -A | grep sendmail | wc -l
echo
echo ./processes kworker
./processes kworker

echo "ps -A | grep kworker | wc -l"
ps -A | grep kworker | wc -l
