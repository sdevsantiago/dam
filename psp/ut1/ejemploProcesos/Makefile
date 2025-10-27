# ******************************* Output files ******************************* #

NAME		= procesos

# ************************** Compilation variables *************************** #

JAVAC	= javac
CFLAGS	= -Werror

MAKE += --no-print-directory

# ****************************** Source files ******************************** #

SRC_PATH = src
SRCS = $(shell find $(SRC_PATH) -type f -name "*.java")

# ****************************** Object files ******************************** #

CLASS_PATH	= build
CLASSES := $(patsubst $(SRC_DIR)/%.java, $(BUILD_DIR)/%.class, $(SRCS))

$(CLASS_PATH)/%.class: $(SRC_PATH)/%.java
	@mkdir -p $(CLASS_PATH)
	@$(JAVAC) -d $(CLASS_PATH) $(CFLAGS) $<
	@echo "$< compiled"

# ********************************* Rules ************************************ #

all: $(NAME)
.PHONY: all

$(NAME): $(CLASS)
	@$(JAVAC) $(CFLAGS) $(CLASS)
	@$(PRINTF) "$(GREEN)$(EMOJI_CHECK) $(NAME) ready. $(RESET)"

clean:
	@rm -rf $(CLASS_PATH)
.PHONY: clean

fclean: clean
	@rm -f $(NAME)
.PHONY: fclean

re: fclean all
.PHONY: re

# ***************************** Style variables ****************************** #

PRINTF = printf "%b\n"

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
