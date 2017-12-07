/*
 * Name: $(projectName).c
 * Author: $(module.author)
 * Description: $(module.description)
 *
 * */

#include <linux/module.h>
#include <linux/of_device.h>
#include <linux/platform_device.h>


static int $(projectName)_probe(struct platform_device *pdev){
	return 0;
}

static int $(projectName)_remove(struct platform_device * pdev){
	return 0;
}

static const struct of_device_id $(projectName)_ids[] = {
		{ .compatible = "$(platform.compatible)",
		  .data = NULL,
		},

		{}
};

static struct platform_driver $(projectName)_driver = {
		.driver = {
				.name = "$(platform.name)",
				.of_match_table = $(projectName)_ids,
		},
		.probe = $(projectName)_probe,
		.remove = $(projectName)_remove,
};


module_platform_driver($(projectName)_driver);
MODULE_AUTHOR("$(module.author)");
MODULE_DESCRIPTION("$(module.description)");
MODULE_LICENSE("$(module.license)");
