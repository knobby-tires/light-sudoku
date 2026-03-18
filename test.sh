#!/usr/bin/env bash
set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "=== Running Puzzle Validation Tests ==="
gradle-env -c "cd $SCRIPT_DIR/android && gradle test --info 2>&1 | grep -E '(OK|FAIL|PASSED|FAILED|tests)'"

echo ""
echo "Full test report: android/app/build/reports/tests/testDebugUnitTest/index.html"
