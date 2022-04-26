CFLAGS   = -Wall -std=gnu99
INCLUDES = -I .
OBJDIR   = obj

RECEIVER_SRCS = defines.c err_exit.c time.c shared_memory.c semaphore.c pipe.c fifo.c receiver_manager.c R1.c R1_lib.c R2.c R3.c receiver_manager_lib.c
RECEIVER_OBJS = $(addprefix $(OBJDIR)/, $(RECEIVER_SRCS:.c=.o))

SENDER_SRCS = defines.c err_exit.c time.c shared_memory.c semaphore.c pipe.c fifo.c sender_manager.c S1.c S1_lib.c S2.c S3.c sender_manager_lib.c
SENDER_OBJS = $(addprefix $(OBJDIR)/, $(SENDER_SRCS:.c=.o))

HACKLER_SRCS = defines.c err_exit.c hackler.c hackler_lib.c semaphore.c
HACKLER_OBJS = $(addprefix $(OBJDIR)/, $(HACKLER_SRCS:.c=.o))

all: $(OBJDIR) receiver_manager sender_manager hackler

receiver_manager: $(RECEIVER_OBJS)
	@echo "Making executable: "$@
	@$(CC) $^ -o $@  -lm

sender_manager: $(SENDER_OBJS)
	@echo "Making executable: "$@
	@$(CC) $^ -o $@  -lm

hackler: $(HACKLER_OBJS)
	@echo "Making executable: "$@
	@$(CC) $^ -o $@  -lm

$(OBJDIR):
	@mkdir -p $(OBJDIR)

$(OBJDIR)/%.o: %.c
	$(CC) $(CFLAGS) $(CPPFLAGS) -o $@ -c $<

run:
	@ ./sender_manager InputFiles/F0.csv & ./receiver_manager & ./hackler InputFiles/F7.csv

clean:
	@rm -vf ${RECEIVER_OBJS}
	@rm -vf ${SENDER_OBJS}
	@rm -vf ${HACKLER_OBJS}
	@rm -vf receiver_manager
	@rm -vf sender_manager
	@rm -vf hackler
	@rm -rf ${OBJDIR}
	@ipcrm -a
	@echo "Removed object files and executables..."

.PHONY: run clean
