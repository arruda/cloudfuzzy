TARGET?=fuzzy_modeling

test:
	DJANGO_SETTINGS_MODULE=cloudfuzzy.settings PYTHONPATH=. \
		django-admin.py test ${TARGET}

coverage:
	coverage erase
	DJANGO_SETTINGS_MODULE=cloudfuzzy.settings PYTHONPATH=. \
		coverage run --branch --source=. \
		`which django-admin.py` test ${TARGET}
	coverage html
	coverage report
