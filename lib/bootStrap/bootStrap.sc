BootStrap : APP{
	*new{ arg here=thisProcess.nowExecutingPath;
		BootStrap.live(here.postln,\write)
	}
	*clear{ arg here=thisProcess.nowExecutingPath;
		BootStrap.live(here,\clean)
	}
}

