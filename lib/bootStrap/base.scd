{
	arg n; var name=n.capitalize;
	var sig=Here.signature.asString;
	(
		files:(
			app:[  (name++" : APP {"++Char.nl++
				"*new{"++name++".main}"++Char.nl++"}")
				,'sc'],
			main:[{},'scd']
	).put(name.asSymbol,[sig,'quark']),
	dirs:(
		ext:(dirs:(methodOverwrite:())),
		helpSource:(files:(rien:["help",'schelp'])),
		lib:()
	)
	)
}
//TODO generate doc 