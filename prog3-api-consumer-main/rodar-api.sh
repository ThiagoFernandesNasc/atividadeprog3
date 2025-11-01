#!/bin/bash

# Script para rodar a API de Usuários
# Funciona em Windows (Git Bash/WSL), Linux e Mac

clear
echo "========================================"
echo "   🚀 Iniciando API de Usuários 🚀"
echo "========================================"
echo ""

# Função para parar processos Java
stop_java() {
    echo "⏹️  Parando processos Java existentes..."
    if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
        # Windows (Git Bash)
        taskkill //F //IM java.exe 2>&1 | grep -v "não foi encontrado" || true
    else
        # Linux/Mac
        pkill -f java 2>/dev/null || true
    fi
    sleep 2
    echo "✅ Processos Java parados (se houver)"
    echo ""
}

# Função para verificar se Maven está disponível
check_maven() {
    # Prioriza Maven instalado (mvn)
    if command -v mvn &> /dev/null 2>&1; then
        echo "mvn"
    elif [ -f "./mvnw" ]; then
        chmod +x ./mvnw 2>/dev/null || true
        echo "./mvnw"
    elif [ -f "./mvnw.cmd" ]; then
        echo "./mvnw.cmd"
    else
        echo "ERROR"
    fi
}

# Para processos Java existentes
stop_java

# Navega para o diretório do script
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

echo "📂 Diretório: $SCRIPT_DIR"
echo ""

# Verifica Maven
echo "🔍 Verificando Maven..."
MAVEN_CMD=$(check_maven)

if [ "$MAVEN_CMD" = "ERROR" ]; then
    echo "❌ Erro: Maven não encontrado!"
    echo ""
    echo "Opções:"
    echo "  1. Instale o Maven e adicione ao PATH"
    echo "  2. Ou use o Maven Wrapper (mvnw) se disponível"
    exit 1
fi

if [ "$MAVEN_CMD" = "mvn" ]; then
    echo "✅ Maven instalado encontrado (prioridade)"
else
    echo "✅ Maven Wrapper encontrado: $MAVEN_CMD"
fi
echo ""

echo "🔨 Compilando projeto..."
echo "========================================"
echo ""

# Executa a aplicação usando Maven instalado (prioridade) ou wrapper
if [[ "$OSTYPE" == "msys" || "$OSTYPE" == "win32" ]]; then
    # Windows (Git Bash ou PowerShell)
    echo "🌐 Executando no Windows..."
    echo ""
    # Usa Maven instalado se disponível, senão usa wrapper
    if command -v mvn &> /dev/null 2>&1; then
        echo "✅ Usando Maven instalado: $(mvn -version | head -n 1)"
        echo ""
        mvn clean spring-boot:run
    elif [ -f "./mvnw.cmd" ]; then
        echo "⚠️  Usando Maven Wrapper (Maven não encontrado no PATH)"
        echo ""
        exec ./mvnw.cmd clean spring-boot:run
    elif [ -f "./mvnw" ]; then
        echo "⚠️  Usando Maven Wrapper (Maven não encontrado no PATH)"
        echo ""
        bash ./mvnw clean spring-boot:run
    else
        echo "❌ Erro: Maven não encontrado!"
        echo "Instale o Maven ou verifique se está no PATH."
        exit 1
    fi
else
    # Linux/Mac
    echo "🐧 Executando no Linux/Mac..."
    echo ""
    if command -v mvn &> /dev/null 2>&1; then
        echo "✅ Usando Maven instalado: $(mvn -version | head -n 1)"
        echo ""
    fi
    $MAVEN_CMD clean spring-boot:run
fi

