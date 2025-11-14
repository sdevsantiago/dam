PORT ?= 80
PUBLIC_PATH ?= public
ROUTER ?= routes/router.php

server:
	@if [ ${USER} == 'root' ]; then										\
		php --server localhost:$(PORT) -t $(PUBLIC_PATH) $(ROUTER);		\
	else																\
		echo 'Insufficient permissions, run again as sudo';				\
		exit 1;															\
	fi
