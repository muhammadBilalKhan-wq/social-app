import os

def count_lines(filepath):
    try:
        with open(filepath, 'r', encoding='utf-8', errors='ignore') as f:
            return len(f.readlines())
    except:
        return 0

total = 0
backend_total = 0
android_total = 0

for root, dirs, files in os.walk('.'):
    for file in files:
        if file.endswith(('.py', '.kt', '.xml', '.kts', '.toml', '.properties')) and 'migrations' not in root:
            filepath = os.path.join(root, file)
            lines = count_lines(filepath)
            total += lines
            if 'backend' in root:
                backend_total += lines
            elif 'checking_sn' in root:
                android_total += lines

print(f"Total lines of code: {total}")
print(f"Backend (Python) lines: {backend_total}")
print(f"Android (Kotlin) lines: {android_total}")
