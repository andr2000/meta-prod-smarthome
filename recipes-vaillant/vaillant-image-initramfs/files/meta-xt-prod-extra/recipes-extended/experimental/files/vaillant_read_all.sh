VAILLANT_CMDS_READ_ONCE="
	AccessoriesOne \
	AccessoriesTwo \
	AntiCondensValue \
	averageIgnitiontime \
	BlockTimeHcMax \
	BoilerType \
	ChangesDSN \
	CirPump \
	CodingResistor \
	CounterStartattempts1 \
	CounterStartattempts2 \
	CounterStartAttempts3 \
	CounterStartAttempts4 \
	currenterror \
	DateTime \
	dcfState \
	DCFTimeDate \
	DCRoomthermostat \
	DeactivationsIFC \
	DeactivationsTemplimiter \
	DeltaFlowReturnMax \
	DisplayMode \
	DSN \
	DSNOffset \
	DSNStart \
	EbusSourceOn \
	EbusVoltage \
	errorhistory \
	ExternalFaultmessage \
	externalHwcSwitch \
	ExternGasvalve \
	ExtFlowTempDesiredMin \
	ExtStorageModulCon \
	extWP \
	FanHours \
	FanMaxSpeedOperation \
	FanMinSpeedOperation \
	FanPWMSum \
	FanPWMTest \
	FanSpeed \
	FanSpeedOffsetMax \
	FanSpeedOffsetMin \
	FanStarts \
	FlameSensingASIC \
	FloorHeatingContact \
	FlowsetHcMax \
	Fluegasvalve \
	FluegasvalveOpen \
	Gasvalve3UC \
	GasvalveASICFeedback \
	GasvalveUC \
	GasvalveUCFeedback \
	HcHours \
	HcPumpMode \
	HcPumpStarts \
	HcStarts \
	HcUnderHundredStarts \
	HoursTillService \
	HwcDemand \
	HwcHours \
	HwcImpellorSwitch \
	HwcStarts \
	HwcTemp \
	HwcTempMax \
	HwcTypes \
	HwcUnderHundredStarts \
	HwcWaterflowMax \
	InitialisationEEPROM \
	IonisationVoltageLevel \
	maintenancedata_HwcTempMax \
	OptionalRelais \
	OutdoorstempSensor \
	OverflowCounter \
	ParamToken \
	PartnumberBox \
	PositionValveSet \
	PowerValue \
	PrAPSCounter \
	PrAPSSum \
	PrEnergyCountHc1 \
	PrEnergyCountHc2 \
	PrEnergyCountHc3 \
	PrEnergyCountHwc1 \
	PrEnergyCountHwc2 \
	PrEnergyCountHwc3 \
	PrEnergySumHc1 \
	PrEnergySumHc2 \
	PrEnergySumHc3 \
	PrEnergySumHwc1 \
	PrEnergySumHwc2 \
	PrEnergySumHwc3 \
	PrimaryCircuitFlowrate \
	ProductionByte \
	PrVortexFlowSensorValue \
	PumpHours \
	PumpHwcFlowNumber \
	PumpHwcFlowSum \
	RemainingBoilerblocktime \
	SerialNumber \
	SetFactoryValues \
	SHEMaxDeltaHwcFlow \
	SHEMaxFlowTemp \
	status16 \
	status \
	StorageDelay \
	StorageExitTemp \
	Storageloadpump \
	StorageLoadPumpHours \
	StorageloadPumpStarts \
	StorageLoadTimeMax \
	StorageTemp \
	StorageTempDesired \
	StorageTempMax \
	TargetFanSpeed \
	TargetFanSpeedOutput \
	TempDiffBlock \
	TempDiffFailure \
	TempGradientFailure \
	Templimiter \
	TemplimiterWithNTC \
	TempMaxDiffExtTFT \
	Testbyte \
	TimerInputHc \
	ValveStarts \
	VolatileLockout \
	VolatileLockoutIFCGV \
	WarmstartDemand \
	WaterHcFlowMax \
	WaterpressureBranchControlOff \
	WaterpressureMeasureCounter \
	WaterpressureVariantSum \
	WP \
	WPPostrunTime \
	WPSecondStage \
"

VAILLANT_CMDS="\
	Flame \
	FlowTemp \
	FlowTempDesired \
	FlowTempMax \
	HeatingSwitch \
	HwcSwitch \
	Ignitor \
	maxIgnitiontime \
	minIgnitiontime \
	ModulationTempDesired \
	PartloadHcKW \
	PumpPower \
	PumpPowerDesired \
	ReturnRegulation \
	ReturnTemp \
	ReturnTempExternal \
	ReturnTempMax \
	SetMode \
	Statenumber \
	Status01 \
	Status02 \
	WaterPressure \
"

mkdir -p VAILLANT_MNT_DATA/log || true
LOG_FILE="VAILLANT_MNT_DATA/log/vaillant.log"

date=`date +%F%t%T`
echo "$date START ======================================" >> $LOG_FILE
for cmd in $VAILLANT_CMDS_READ_ONCE; do
	out=`ebusctl r -f $cmd`
	echo "$cmd $out" >> $LOG_FILE
done

while true; do
	date=`date +%F%t%T`
	echo "$date ======================================" >> $LOG_FILE
	for cmd in $VAILLANT_CMDS; do
		out=`ebusctl r -f $cmd`
		echo "$cmd $out" >> $LOG_FILE
	done
	sleep 10m
done
