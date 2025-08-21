-- Пользователи
CREATE TABLE users (
                       id BIGSERIAL PRIMARY KEY,
                       phone_number VARCHAR(20) NOT NULL UNIQUE,
                       first_name VARCHAR(100),
                       last_name VARCHAR(100),
                       email VARCHAR(255),
                       role VARCHAR(20) NOT NULL,
                       created_at TIMESTAMP NOT NULL DEFAULT now(),
                       updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Курорты
CREATE TABLE resorts (
                         id BIGSERIAL PRIMARY KEY,
                         owner_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         name VARCHAR(200) NOT NULL,
                         description TEXT,
                         location VARCHAR(200) NOT NULL,
                         price_per_night NUMERIC(12,2) NOT NULL,
                         rating_avg DOUBLE PRECISION NOT NULL DEFAULT 0,
                         rating_count BIGINT NOT NULL DEFAULT 0,
                         contact_phone VARCHAR(50),
                         contact_email VARCHAR(255),
                         contact_website VARCHAR(255),
                         policies TEXT,
                         latitude DOUBLE PRECISION,
                         longitude DOUBLE PRECISION,
                         created_at TIMESTAMP NOT NULL DEFAULT now(),
                         updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Картинки курорта
CREATE TABLE resort_images (
                               resort_id BIGINT NOT NULL REFERENCES resorts(id) ON DELETE CASCADE,
                               image_url VARCHAR(1000) NOT NULL,
                               PRIMARY KEY (resort_id, image_url)
);

-- Удобства курорта
CREATE TABLE resort_amenities (
                                  resort_id BIGINT NOT NULL REFERENCES resorts(id) ON DELETE CASCADE,
                                  amenity VARCHAR(50) NOT NULL,
                                  PRIMARY KEY (resort_id, amenity)
);

-- Доступные даты
CREATE TABLE resort_availability (
                                     id BIGSERIAL PRIMARY KEY,
                                     resort_id BIGINT NOT NULL REFERENCES resorts(id) ON DELETE CASCADE,
                                     date DATE NOT NULL,
                                     is_available BOOLEAN NOT NULL,
                                     created_at TIMESTAMP NOT NULL DEFAULT now(),
                                     updated_at TIMESTAMP NOT NULL DEFAULT now(),
                                     UNIQUE(resort_id, date)
);

-- Бронирования
CREATE TABLE bookings (
                          id BIGSERIAL PRIMARY KEY,
                          resort_id BIGINT NOT NULL REFERENCES resorts(id) ON DELETE CASCADE,
                          user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                          check_in_date DATE NOT NULL,
                          check_out_date DATE NOT NULL,
                          guests INT NOT NULL,
                          total_price NUMERIC(12,2) NOT NULL,
                          status VARCHAR(20) NOT NULL,
                          special_requests VARCHAR(1000),
                          created_at TIMESTAMP NOT NULL DEFAULT now(),
                          updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Отзывы
CREATE TABLE reviews (
                         id BIGSERIAL PRIMARY KEY,
                         user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                         resort_id BIGINT NOT NULL REFERENCES resorts(id) ON DELETE CASCADE,
                         rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
                         comment VARCHAR(2000),
                         created_at TIMESTAMP NOT NULL DEFAULT now(),
                         updated_at TIMESTAMP NOT NULL DEFAULT now()
);

-- Избранные
CREATE TABLE favorites (
                           id BIGSERIAL PRIMARY KEY,
                           user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           resort_id BIGINT NOT NULL REFERENCES resorts(id) ON DELETE CASCADE,
                           created_at TIMESTAMP NOT NULL DEFAULT now(),
                           updated_at TIMESTAMP NOT NULL DEFAULT now(),
                           UNIQUE(user_id, resort_id)
);
