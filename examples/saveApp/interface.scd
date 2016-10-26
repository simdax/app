Merde.write
Merde.read(Merde.root+/+"data.scd")
Merde.read
Library.postTree

EZListView().items_(
	["niou Save" -> {Merde.default}] ++
	PathName(Merde.saveDir).files.collect({|x| 
		x.fileName -> {Merde.read(x.absolutePath)}
	})
)

;