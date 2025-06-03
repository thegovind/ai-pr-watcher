#!/usr/bin/env python3
"""
Example usage of the Account database schema.
Demonstrates COBOL VSAM to PostgreSQL conversion patterns.
"""

from decimal import Decimal
from database.config import engine, SessionLocal
from database.models import Base, Account
from database.operations import AccountOperations

def setup_database():
    """Create all database tables"""
    Base.metadata.create_all(bind=engine)
    print("Database tables created successfully")

def example_operations():
    """Demonstrate basic account operations"""
    db = SessionLocal()
    
    try:
        print("\n=== Account Creation ===")
        account1 = AccountOperations.create_account(
            db, 
            account_number="ACC001234567890", 
            pin="1234", 
            initial_balance=Decimal('1000.00')
        )
        
        if account1:
            print(f"Created account: {account1.account_number} with balance: ${account1.balance}")
        else:
            print("Failed to create account (may already exist)")
        
        account2 = AccountOperations.create_account(
            db,
            account_number="ACC987654321098",
            pin="5678",
            initial_balance=Decimal('500.50')
        )
        
        if account2:
            print(f"Created account: {account2.account_number} with balance: ${account2.balance}")
        
        print("\n=== Authentication ===")
        auth_account = AccountOperations.authenticate_account(db, "ACC001234567890", "1234")
        if auth_account:
            print(f"Authentication successful for account: {auth_account.account_number}")
        else:
            print("Authentication failed")
        
        wrong_pin = AccountOperations.authenticate_account(db, "ACC001234567890", "9999")
        if wrong_pin:
            print("Authentication successful")
        else:
            print("Authentication failed with wrong PIN (expected)")
        
        print("\n=== Balance Operations ===")
        success = AccountOperations.deposit(db, "ACC001234567890", Decimal('250.75'))
        if success:
            updated_account = AccountOperations.get_account(db, "ACC001234567890")
            print(f"Deposit successful. New balance: ${updated_account.balance}")
        
        success = AccountOperations.withdraw(db, "ACC001234567890", Decimal('100.00'))
        if success:
            updated_account = AccountOperations.get_account(db, "ACC001234567890")
            print(f"Withdrawal successful. New balance: ${updated_account.balance}")
        
        print("\n=== All Accounts ===")
        all_accounts = AccountOperations.get_all_accounts(db)
        for account in all_accounts:
            print(f"Account: {account.account_number}, Balance: ${account.balance}, Created: {account.created_at}")
        
    finally:
        db.close()

if __name__ == "__main__":
    print("Setting up database...")
    setup_database()
    
    print("Running example operations...")
    example_operations()
    
    print("\nExample completed successfully!")
    print("\nTo use with Alembic migrations instead:")
    print("1. Set DATABASE_URL environment variable")
    print("2. Run: alembic upgrade head")
    print("3. Use the AccountOperations class for database operations")
