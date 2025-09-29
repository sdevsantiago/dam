# ******************************* Output files ******************************* #

NAME		= calentando

# ************************** Compilation variables *************************** #

JAVAC	= javac
CFLAGS	= -Werror 

MAKE += --no-print-directory

# ****************************** Source files ******************************** #

SRC_PATH = src

SRC = $(shell find $(SRC_PATH) -type f -name "*.java")

# ****************************** Object files ******************************** #

CLASS_PATH	= build
CLASS		= $(SRC:$(SRC_PATH)/%.c=$(CLASS_PATH)/%.o)

$(CLASS_PATH)/%.class: $(SRC_PATH)/%.java
	@mkdir -p $(@D)
	@$(JAVAC) $(CFLAGS) $< -d $(CLASS_PATH)
	@echo "$< compiled"

# ********************************* Rules ************************************ #

all: $(NAME)
.PHONY: all

$(NAME): $(OBJS)
	@$(JAVAC) $(CFLAGS) $(CLASS)
	@echo "$(GREEN)$(EMOJI_CHECK) $(NAME) ready. $(RESET)"

clean:
	@rm -rf $(CLASS_PATH)
.PHONY: clean

fclean: clean
	@rm -f $(NAME)
.PHONY: fclean

re: fclean all
.PHONY: re

# ***************************** Style variables ****************************** #

RED = \033[0;31m
GREEN = \033[0;32m
YELLOW = \033[0;33m
BLUE = \033[0;34m
RESET = \033[0m

EMOJI_BROOM = 🧹
EMOJI_CHECK = ✅
EMOJI_CROSS = ❌
EMOJI_WRENCH = 🔧
EMOJI_BOX = 📦
