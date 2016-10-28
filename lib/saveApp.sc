// dataApp is a simple class for settings default params
// it has, like Archive, automatic read/write with startup/shutdown

DataAPP : ArchiveAPP {

	*initClass{
		Class.initClassTree(Library);
		StartUp.add{this.subclasses.do(_.read)};
		ShutDown.add{this.subclasses.do(_.write)};
	}

}


// saveApp is a way to save presets
//saveAs save in a file
SaveAPP : ArchiveAPP{
	classvar <saveDir, <data;
	classvar panel;
	*initClass{
		this.subclasses.do(_.createSaveDir);
	} 
	*createSaveDir{
		//if already exists, do nothing
		saveDir=(this.root+/+"saves").mkdir;
		data=(this.root+/+"data.scd").load;
	}

	*saveAs{ arg name;
		var envir=
		(data ? ()).putAll(currentEnvironment.as(Event));
		envir.postln;
		name ?? { name=
			Date.getDate.format("%D"++"@"++"%r")
			.replace("/","_") // name compatibility
		};
		super.save(envir);
		super.write(saveDir+/+name++".sctxar");
	}
	*panel{ arg p,b;
		if(panel.isNil, {
			var f, h, g, daemon;
			f={
				"(default"++if(File.exists(Merde.root+/+"data.scd"))
				{" exists)"}{" is empty)"}
			};
			h={[("new"++f.value) -> {
				super.clear; this.save(data)
			}]
				++
				PathName(saveDir).files.collect({|x| 
					x.fileNameWithoutExtension -> {
						this.read(x.absolutePath);
						Library.at(this.name).postln
					}
				})
			};
			panel=EZListView().items_(h.value)
			.onClose_{daemon.stop; panel=nil};
			panel.widget.keyDownAction_{arg s, c,mod,uni;
				uni.postln;
				case(
					{uni==13}, {panel.valueAction_(g.value)},
					{uni==8}, {(saveDir+/+panel.item).postln}// {s.item}
				)
			};			
			daemon=fork({var items;
				items=g.items;
				loop{
					if(PathName(saveDir).files.collect(_.fileNameWithoutExtension)!=g.items.drop(1).collect(_.key), {
						g.items_(h.value)
					});
					1.wait
				}},AppClock)
		});
	}
	/// WARNING !!!!
	*clearAll{
		PathName(Merde.saveDir).filesDo { |x|
			File.delete(x.absolutePath)	
		};
	}
}