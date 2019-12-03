VAILLANT_CMDS_READ_ALL=`ebusctl f -F circuit,name | grep bai | awk -F "," '{ print $2 }'`

for cmd in $VAILLANT_CMDS_READ_ALL; do
	out=`ebusctl r -f $cmd`
	echo "$cmd $out"
done
