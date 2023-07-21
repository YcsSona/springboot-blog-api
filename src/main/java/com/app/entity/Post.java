package com.app.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

	public Post(String title, String description, String content) {
		super();
		this.title = title;
		this.description = description;
		this.content = content;
	}

	@Column(unique = true, nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String content;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> comments = new HashSet<>();

}
