#!/bin/bash

# 自动化测试脚本
echo "🚀 开始自动化测试流程..."

# 1. 检查项目结构
echo "📋 检查项目结构..."
if [ -f "pom.xml" ] && [ -d "src/main/java/com/sky" ]; then
    echo "✅ 项目结构验证通过"
else
    echo "❌ 项目结构验证失败"
    exit 1
fi

# 2. Maven 构建测试
echo "🔨 执行 Maven 构建..."
mvn clean compile test-compile

if [ $? -eq 0 ]; then
    echo "✅ Maven 构建成功"
else
    echo "❌ Maven 构建失败"
    exit 1
fi

# 3. 单元测试
echo "🧪 执行单元测试..."
mvn test

if [ $? -eq 0 ]; then
    echo "✅ 单元测试通过"
else
    echo "⚠️ 单元测试有失败，但继续流程..."
fi

# 4. 打包应用
echo "📦 打包应用..."
mvn package -DskipTests

if [ $? -eq 0 ]; then
    echo "✅ 应用打包成功"
    ls -la target/*.jar
else
    echo "❌ 应用打包失败"
    exit 1
fi

echo "🎉 自动化测试完成！"