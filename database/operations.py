from sqlalchemy.orm import Session
from sqlalchemy.exc import IntegrityError
from database.models import Account
from database.config import get_db
from decimal import Decimal
from typing import Optional, List

class AccountOperations:
    
    @staticmethod
    def create_account(db: Session, account_number: str, pin: str, initial_balance: Decimal = Decimal('0.00')) -> Optional[Account]:
        try:
            account = Account(
                account_number=account_number,
                balance=initial_balance
            )
            account.set_pin(pin)
            
            db.add(account)
            db.commit()
            db.refresh(account)
            return account
        except IntegrityError:
            db.rollback()
            return None
    
    @staticmethod
    def get_account(db: Session, account_number: str) -> Optional[Account]:
        return db.query(Account).filter(Account.account_number == account_number).first()
    
    @staticmethod
    def authenticate_account(db: Session, account_number: str, pin: str) -> Optional[Account]:
        account = AccountOperations.get_account(db, account_number)
        if account and account.verify_pin(pin):
            return account
        return None
    
    @staticmethod
    def update_balance(db: Session, account_number: str, new_balance: Decimal) -> bool:
        account = AccountOperations.get_account(db, account_number)
        if account:
            account.balance = new_balance
            db.commit()
            return True
        return False
    
    @staticmethod
    def deposit(db: Session, account_number: str, amount: Decimal) -> bool:
        account = AccountOperations.get_account(db, account_number)
        if account and amount > 0:
            account.balance += amount
            db.commit()
            return True
        return False
    
    @staticmethod
    def withdraw(db: Session, account_number: str, amount: Decimal) -> bool:
        account = AccountOperations.get_account(db, account_number)
        if account and amount > 0 and account.balance >= amount:
            account.balance -= amount
            db.commit()
            return True
        return False
    
    @staticmethod
    def get_all_accounts(db: Session) -> List[Account]:
        return db.query(Account).all()
    
    @staticmethod
    def delete_account(db: Session, account_number: str) -> bool:
        account = AccountOperations.get_account(db, account_number)
        if account:
            db.delete(account)
            db.commit()
            return True
        return False
