from sqlalchemy import Column, String, DECIMAL, DateTime, Index
from sqlalchemy.sql import func
from database.config import Base
import bcrypt

class Account(Base):
    __tablename__ = "accounts"
    
    account_number = Column(String(20), primary_key=True, nullable=False)
    hashed_pin = Column(String(255), nullable=False)
    balance = Column(DECIMAL(15, 2), nullable=False, default=0.00)
    created_at = Column(DateTime(timezone=True), server_default=func.now(), nullable=False)
    updated_at = Column(DateTime(timezone=True), server_default=func.now(), onupdate=func.now(), nullable=False)
    
    __table_args__ = (
        Index('idx_account_number', 'account_number'),
        Index('idx_created_at', 'created_at'),
    )
    
    def set_pin(self, pin: str) -> None:
        salt = bcrypt.gensalt()
        self.hashed_pin = bcrypt.hashpw(pin.encode('utf-8'), salt).decode('utf-8')
    
    def verify_pin(self, pin: str) -> bool:
        return bcrypt.checkpw(pin.encode('utf-8'), self.hashed_pin.encode('utf-8'))
    
    def __repr__(self):
        return f"<Account(account_number='{self.account_number}', balance={self.balance})>"
