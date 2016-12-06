{
	arg n; var name=n.capitalize;
	(
		dirs:(
			helpSource:(files:()
				.put(name,
					SCDoc.makeClassTemplate(
						SCDocEntry.newUndocClass(name.asSymbol.asClass)
					).replace("(some example code)",
						name.asClass.perform("tests")
					),			
					'schelp']
			),
		)
	)
}
