/*
 * Name: $(projectName).c
 * Author: $(module.author)
 * Description: $(module.description)
 *
 * */

#include <linux/module.h>

int $(projectName)_init(void){
	return 0;
}

void $(projectName)_exit(void){
	return;
}


module_init($(projectName)_init);
module_exit($(projectName)_exit);
MODULE_AUTHOR("$(module.author)");
MODULE_DESCRIPTION("$(module.description)");
MODULE_LICENSE("$(module.license)");
