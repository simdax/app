{
	arg n; var name=n.capitalize;
	var sig=BootStrap.signature.asString;
	(
		files:(
			app:[  (name++" : APP {"++Char.nl++
				"*new{^"++name++".main}"++Char.nl++"}")
				,'sc'],
			main:[{},'scd']
	).put(name.asSymbol,[sig,'quark']),
	dirs:(
		ext:(dirs:(SystemOverwrites:())),
		helpSource:(files:(rien:["help",'schelp'])),
		lib:()
	)
	)
}
//TODO generate doc 