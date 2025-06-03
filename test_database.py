#!/usr/bin/env python3
"""Test script to verify database functionality"""

import sys
sys.path.append('.')

from database.config import Base, engine
from database.models import Account

def test_imports():
    print("Testing database imports...")
    try:
        from database.config import Base, engine
        from database.models import Account
        print("✅ Database models imported successfully")
        return True
    except Exception as e:
        print(f"❌ Import failed: {e}")
        return False

def test_table_creation():
    print("Testing table creation...")
    try:
        Base.metadata.create_all(bind=engine)
        print("✅ Database tables created successfully")
        return True
    except Exception as e:
        print(f"❌ Table creation failed: {e}")
        return False

if __name__ == "__main__":
    success = True
    success &= test_imports()
    success &= test_table_creation()
    
    if success:
        print("\n🎉 All database tests passed!")
    else:
        print("\n❌ Some tests failed")
        sys.exit(1)
