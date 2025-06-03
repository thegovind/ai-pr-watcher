"""Create accounts table

Revision ID: 001
Revises: 
Create Date: 2025-06-03 01:00:00.000000

"""
from alembic import op
import sqlalchemy as sa
from sqlalchemy.dialects import postgresql

revision = '001'
down_revision = None
branch_labels = None
depends_on = None


def upgrade() -> None:
    op.create_table('accounts',
    sa.Column('account_number', sa.String(length=20), nullable=False),
    sa.Column('hashed_pin', sa.String(length=255), nullable=False),
    sa.Column('balance', sa.DECIMAL(precision=15, scale=2), nullable=False, server_default='0.00'),
    sa.Column('created_at', sa.DateTime(timezone=True), server_default=sa.text('now()'), nullable=False),
    sa.Column('updated_at', sa.DateTime(timezone=True), server_default=sa.text('now()'), nullable=False),
    sa.PrimaryKeyConstraint('account_number')
    )
    op.create_index('idx_account_number', 'accounts', ['account_number'], unique=False)
    op.create_index('idx_created_at', 'accounts', ['created_at'], unique=False)


def downgrade() -> None:
    op.drop_index('idx_created_at', table_name='accounts')
    op.drop_index('idx_account_number', table_name='accounts')
    op.drop_table('accounts')
