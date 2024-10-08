class AdminOnlyResourcePolicy < ApplicationPolicy
  def index?
    pp user.role
    user.role == "admin"
  end
  def show?
    user.role == "admin"
  end
  def new?
    user.role == "admin"
  end
  def edit?
    user.role == "admin"
  end
  def create?
    user.role == "admin"
  end
  def update?
    user.role == "admin"
  end
  def destroy?
    user.role == "admin"
  end
end

