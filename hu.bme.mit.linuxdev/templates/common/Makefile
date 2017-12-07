obj-m += $(projectName).o

all:
	make  ARCH=$(module.arch) CROSS_COMPILE=$(module.ccprefix) -C $(module.kerneldir) M=$(PWD) modules

clean:
	make  ARCH=$(module.arch) CROSS_COMPILE=$(module.ccprefix) -C $(module.kerneldir) M=$(PWD) clean
