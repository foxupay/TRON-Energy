#!/bin/bash

# 获取脚本的绝对路径和所在目录
SCRIPT_PATH=$(readlink -f "$0")
SCRIPT_DIR=$(dirname "$SCRIPT_PATH")

# 定义源日志文件的相对路径，相对于脚本所在目录
LOG_FILE="nohup.out"

# 定义目标日志文件夹的相对路径，相对于执行脚本的目录
LOG_DIR="outs"

# 创建日志文件夹，如果不存在
mkdir -p "${SCRIPT_DIR}/${LOG_DIR}"

# 获取当前日期
now_date=$(date +%Y%m%d_%H%M%S)

# 构建目标日志文件的完整路径
DEST_PATH="${SCRIPT_DIR}/${LOG_DIR}/${now_date}.log"

# 输出目标日志文件的路径
echo "dest log path: ${DEST_PATH}"

# 构建源日志文件的完整路径
SOURCE_LOG_PATH="${SCRIPT_DIR}/${LOG_FILE}"

# 复制源日志文件到目标日志文件
cp -axv "$SOURCE_LOG_PATH" "${DEST_PATH}"

# 清空源日志文件
cat /dev/null > "$SOURCE_LOG_PATH"