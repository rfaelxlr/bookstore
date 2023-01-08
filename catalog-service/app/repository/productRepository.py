from sqlalchemy.orm import Session
from sqlalchemy.sql import select, insert
from sqlalchemy import func
from sqlalchemy.sql.dml import Insert


from models.entity.product import Product
import math
from models.schema.pageResponse import PageResponse
from models.schema.createProduct import CreateProduct


async def get_products(db: Session,  page: int = 1,
                       limit: int = 10):

    query = select([Product.external_id,
                   Product.code, Product.name, Product.description,
                   Product.img_url, Product.price,
                   Product.discount])

    count_query = select(func.count(1)).select_from(query)

    offset_page = page - 1

    query = (query.offset(offset_page * limit).limit(limit))

    total_record = db.execute(count_query).scalar() or 0

    total_page = math.ceil(total_record / limit)

    result = db.execute(query).fetchall()

    return PageResponse(
        page_number=page,
        page_size=limit,
        total_pages=total_page,
        total_record=total_record,
        content=result
    )


async def get_product_by_code(code: str, db: Session):
    query = select([Product.external_id,
                   Product.code, Product.name, Product.description,
                   Product.img_url, Product.price,
                   Product.discount]).where(Product.code == code)

    return db.execute(query).fetchone()


async def get_products_by_keyword(keyword: str, db: Session,  page: int = 1,
                                  limit: int = 10):

    query = select([Product.external_id,
                   Product.code, Product.name, Product.description,
                   Product.img_url, Product.price,
                   Product.discount]).where(Product.name.ilike('%' + keyword + '%')
                                            | Product.description.ilike('%' + keyword + '%'))
    keyword

    count_query = select(func.count(1)).select_from(query)

    offset_page = page - 1

    query = (query.offset(offset_page * limit).limit(limit))

    total_record = db.execute(count_query).scalar() or 0

    total_page = math.ceil(total_record / limit)

    result = db.execute(query).fetchall()

    return PageResponse(
        page_number=page,
        page_size=limit,
        total_pages=total_page,
        total_record=total_record,
        content=result
    )


async def create(form: CreateProduct, db: Session):
    db.add(Product(code=form.code, name=form.name, description=form.description, img_url=form.image_url, price=form.price))
    try:
        db.commit()
    except Exception:
        db.rollback()
        raise
    